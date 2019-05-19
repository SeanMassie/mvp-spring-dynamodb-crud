package com.seanmassie.mvpspringdynamodbcrud

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.model.*
import com.amazonaws.util.StringUtils
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean


@Configuration
@EnableDynamoDBRepositories(basePackageClasses = [UrlRepository::class])
class DynamoProperties {

    @Value("\${amazon.dynamodb.endpoint}")
    private val amazonDynamoDBEndpoint: String? = null

    @Value("\${amazon.aws.accesskey}")
    private val amazonAWSAccessKey: String? = null

    @Value("\${amazon.aws.secretkey}")
    private val amazonAWSSecretKey: String? = null

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        val amazonDynamoDB = AmazonDynamoDBClient(amazonAWSCredentials())

        if (!StringUtils.isNullOrEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint)
        }
        // TODO: write logic to conditionally create table
//        val keySchema = KeySchemaElement().withAttributeName("shortUrl").withKeyType(KeyType.HASH)
//        val attribute = AttributeDefinition().withAttributeName("shortUrl").withAttributeType(ScalarAttributeType.S)
//        val throughput = ProvisionedThroughput().withReadCapacityUnits(10L).withWriteCapacityUnits(10L)
//        val request = CreateTableRequest().withTableName("Url").withKeySchema(arrayListOf(keySchema))
//                .withProvisionedThroughput(throughput).withAttributeDefinitions(attribute)

//        amazonDynamoDB.createTable(request)

        return amazonDynamoDB
    }

    @Bean
    fun amazonAWSCredentials(): AWSCredentials {
        return BasicAWSCredentials(
                amazonAWSAccessKey!!, amazonAWSSecretKey!!)
    }
}