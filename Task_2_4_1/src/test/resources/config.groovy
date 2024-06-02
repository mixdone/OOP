import java.time.LocalDate

settings = "main"

tasks = [
        {
            name = "Task_1_5_1"
            description = "Calculator"
            softDeadline = LocalDate.of(2023, 12, 8)
            hardDeadline = LocalDate.of(2023, 12, 15)
        },
]

groups = [
        {
            number = 22214
            students = [
                    {
                        name = "Michael Diza"
                        username = "mixdone"
                        repository = "https://github.com/mixdone/OOP.git"
                    },
                    {
                        name = "Matvey Goncharov"
                        username = "ZuZKho"
                        repository = "https://github.com/ZuZKho/OOP.git"
                    }
            ]
        }
]
