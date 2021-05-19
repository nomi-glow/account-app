import graphql.ExecutionResult
import graphql.GraphQL
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController (private val graphQL: GraphQL) {

    @PostMapping("/graphql")
    @ResponseBody
    fun execute(@RequestBody request: Map<String, String>): ExecutionResult {
        return graphQL
                .execute(
                        request["query"].toString()
                )
    }


}