<!DOCTYPE html>
<html lang="en">
<head>
    <title>Task Results</title>
</head>
<body>
    <#list tasks as task>
        <table border = 1>
            <tr>
                <th>${task.name}</th>
            </tr>
            <tr>
                <th>Name</th>
                <th>Build</th>
                <th>JavaDoc</th>
                <th>Checkstyle</th>
                <th>Tests</th>
                <th>Mark</th>
            </tr>
            <#list tasksResults as student, tasks>
                <#list tasks as task1, result>
                    <#if task1.name == task.name>
                    <tr>
                        <th>${student.name}</th>
                        <th><#if result.build>
                           <b>+</b>
                        <#else>
                            <b>-</b>
                        </#if>
                        </th>
                        <th><#if result.javadoc>
                           <b>+</b>
                        <#else>
                            <b>-</b>
                        </#if>
                        </th>
                        <th><#if result.checkstyle>
                           <b>+</b>
                        <#else>
                            <b>-</b>
                        </#if>
                        </th>
                        <th>${result.passedTests}/${result.skippedTests}/${result.failedTests}</th>
                        <th>${result.mark}</th>
                    </tr>
                    <#else>
                    </#if>
                </#list>
            </#list>
        </table>
    </#list>
    <h1>Total Result</h1>
    <table border = 1>
            <tr>
                <th>Name</th>
                <th>Total</th>
            </tr>
        <#list tasksResults as student, tasks>
                <#assign count = 0>
                <#list tasks as task1, result>
                           <#assign count = count + result.mark>
                </#list>
                <tr>
                    <tr>
                    <th>${student.name}</th>
                    <th>${count}</th>
                </tr>
        </#list>
    </table>
</body>
