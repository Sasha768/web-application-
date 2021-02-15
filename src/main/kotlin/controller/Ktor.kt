package controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.thymeleaf.*
import model.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.io.File

fun getResult(answers: MutableList<Answer>): String {
    val size = answers.size
    var countTrue = 0.0f

    answers.forEach { if (it.answerCurrent == it.correctAnswer) countTrue++ }

    val temp = if (countTrue != 0.0f) (countTrue / size).toDouble() * 100 else 0.0

    if (temp > 60 && temp < 80) return "Not bad, but you can better $temp%"

    if (temp > 80) return "Excellent, continue in this pace $temp%"

    return "You need more hard work $temp%"
}

fun main() {


    val server = embeddedServer(Netty, port = 9090) {

        val question1 = "What is tag use for new line with space?"
        val question2 = "Whats tags block?"
        val question3 = "Which of the following tags is not the main one?"
        val question4 = "Which tag sets the paragraph?"
        val question5 = "Select the line feed tag:"
        val question6 = "The value of the align attribute cannot be?"
        val question7 = "Which of the following is not a parameter of the font tag"
        val question8 = "Which tag sets the table row?"
        val question9 = "What tag can I use to insert a drawing?"
        val question10 = "HTML is"
        var person = Person(name = null, age = null)
        val answers: MutableList<Answer> = mutableListOf()


        routing {
            install(Thymeleaf) {
                setTemplateResolver(
                        ClassLoaderTemplateResolver().apply {
                            prefix = "pages/"
                            suffix = ".html"
                            characterEncoding = "utf-8"
                        }
                )
            }

            get("/") { call.respondFile(File("./src/main/resources/pages/main.html")) }

            post("/main") {
                val parameters = call.receiveParameters()
                val name = parameters["name"].toString()
                val age = parameters["age"].toString()
                person = Person(name = name, age = age)
                call.respond(ThymeleafContent("question1", mapOf("question" to question1)))
            }

            post("/answer1") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "1",
                                answerCurrent = answer,
                                correctAnswer = "p",
                                question = question1)
                )
                call.respond(ThymeleafContent("question2", mapOf("question" to question2)))
            }

            post("/answer2") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "2",
                                answerCurrent = answer,
                                correctAnswer = "div, p, ul, ol",
                                question = question2)
                )

                call.respond(ThymeleafContent("question3", mapOf("question" to question3)))

            }

            post("/answer3") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "3",
                                answerCurrent = answer,
                                correctAnswer = "Caption",
                                question = question3)
                )

                call.respond(ThymeleafContent("question4", mapOf("question" to question4)))

            }

            post("/answer4") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "4",
                                answerCurrent = answer,
                                correctAnswer = "p",
                                question = question4)
                )

                call.respond(ThymeleafContent("question5", mapOf("question" to question5)))

            }

            post("/answer5") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "5",
                                answerCurrent = answer,
                                correctAnswer = "br",
                                question = question5)
                )

                call.respond(ThymeleafContent("question6", mapOf("question" to question6))
                )

            }

            post("/answer6") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "6",
                                answerCurrent = answer,
                                correctAnswer = "top",
                                question = question6)
                )

                call.respond(ThymeleafContent("question7", mapOf("question" to question7))
                )

            }

            post("/answer7") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "7",
                                answerCurrent = answer,
                                correctAnswer = "border",
                                question = question7)
                )

                call.respond(ThymeleafContent("question8", mapOf("question" to question8)))

            }

            post("/answer8") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "8",
                                answerCurrent = answer,
                                correctAnswer = "tr",
                                question = question8)
                        )

                call.respond(ThymeleafContent("question9", mapOf("question" to question9)))

            }

            post("/answer9") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "9",
                                answerCurrent = answer,
                                correctAnswer = "img",
                                question = question9)
                )

                call.respond(ThymeleafContent("question10", mapOf("question" to question10)))
            }
            post("/answer10") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                        Answer(
                                numberQuestion = "10",
                                answerCurrent = answer,
                                correctAnswer = "hypertext markup language",
                                question = question10)
                )

                call.respond(ThymeleafContent("end", mapOf("user" to person, "result" to getResult(answers),
                        "answer" to answers)))
                answers.clear()
            }
        }
    }
    server.start(wait = true)
}

