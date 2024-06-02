package task.groovy;

import groovy.lang.*;
import groovy.util.DelegatingScript;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.codehaus.groovy.control.CompilerConfiguration;
import task.Application;

/**
 * Groovy class.
 */
@EqualsAndHashCode(callSuper=false)
@Data
public class Groovy extends GroovyObjectSupport {
    private URI scriptPath;

    @SneakyThrows
    public void runFrom(URI uri) {
        this.scriptPath = uri;
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Application.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = (DelegatingScript) sh.parse(uri);
        script.setDelegate(this);
        script.run();
    }

    @SneakyThrows
    public void postProcess() {
        for (MetaProperty metaProperty : getMetaClass().getProperties()) {
            Object value = getProperty(metaProperty.getName());
            if (Collection.class.isAssignableFrom(metaProperty.getType())
                    && value instanceof Collection) {
                ParameterizedType collectionType = (ParameterizedType) getClass()
                        .getDeclaredField(metaProperty.getName()).getGenericType();
                Class itemClass = (Class) collectionType.getActualTypeArguments()[0];
                if (Groovy.class.isAssignableFrom(itemClass)) {
                    Collection collection = (Collection) value;
                    Collection newValue = collection.getClass()
                            .getConstructor().newInstance();
                    for (Object o : collection) {
                        if (o instanceof Closure) {
                            Object item = itemClass.getConstructor().newInstance();
                            ((Groovy) item).setProperty("scriptPath", scriptPath);
                            ((Closure<?>) o).setDelegate(item);
                            ((Closure<?>) o).setResolveStrategy(Closure.DELEGATE_FIRST);
                            ((Closure<?>) o).call();
                            ((Groovy) item).postProcess();
                            newValue.add(item);
                        } else {
                            newValue.add(o);
                        }
                    }
                    setProperty(metaProperty.getName(), newValue);
                }
            }
        }
    }
}