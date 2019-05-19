package com.seanmassie.mvpspringdynamodbcrud

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable


@DynamoDBTable(tableName = "Url")
public class ShortUrl {
    @get:DynamoDBHashKey
    var shortUrl: String? = null
    @get:DynamoDBAttribute
    var url: String? = null
}