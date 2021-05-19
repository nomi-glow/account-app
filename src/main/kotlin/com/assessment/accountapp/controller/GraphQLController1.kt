//package com.assessment.accountapp.controller
//
//import com.assessment.accountapp.query.PersonQuery
//import graphql.ExecutionInput;
//import graphql.ExecutionResult;
//import graphql.GraphQL;
//import graphql.schema.GraphQLSchema
//import io.leangen.graphql.GraphQLSchemaGenerator;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import javax.servlet.http.HttpServletRequest
//
//
//
//@RestController
//class GraphQLController1 (personQuery: PersonQuery) {
//    var schema :GraphQLSchema = GraphQLSchemaGenerator().generate();
//   var  graphQL: GraphQL = GraphQL.newGraphQL(schema).build()
//
//
//       init{
//
//              schema =  GraphQLSchemaGenerator()
//                    .withBasePackages("com.assessment.accountapp")
//                    .withOperationsFromSingletons(personQuery)
//                    .generate();
//            var graphQL = GraphQL.newGraphQL(schema).build();
//        }
//
//        @PostMapping( "/graphql", MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_JSON_VALUE)
//        @ResponseBody
//        fun indexFromAnnotated(@RequestBody request: Map<String, String> ,  raw: HttpServletRequest) : Map<String, Any>  {
//             val executionResult: ExecutionResult= graphQL.execute(ExecutionInput.newExecutionInput()
//                    .query(request["query"])
//                    .operationName(request["operationName"])
//                    .context(raw)
//                    .build());
//            return executionResult.toSpecification();
//        }
//    }
//
