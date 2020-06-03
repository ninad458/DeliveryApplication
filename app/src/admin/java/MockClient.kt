package com.enigma.myapplication

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        val responseString = when {
            uri.endsWith("tasks") -> getListOfReposBeingStarredJson
            else -> ""
        }
        // Thread.sleep(3000)
        return Response.Builder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .request(chain.request())
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    responseString.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}

const val getListOfReposBeingStarredJson = """
{
  "orders": [
    {
      "id": "asd100001",
      "status": "queued"
    },
    {
      "id": "asd100002",
      "status": "in-transit"
    },
    {
      "id": "asd100003",
      "status": "delivered"
    },
    {
      "id": "asd100004",
      "status": "cancelled"
    },
    {
      "id": "asd100005",
      "status": "queued"
    },
    {
      "id": "asd100006",
      "status": "queued"
    },
    {
      "id": "asd100007",
      "status": "queued"
    }
  ]
}
"""