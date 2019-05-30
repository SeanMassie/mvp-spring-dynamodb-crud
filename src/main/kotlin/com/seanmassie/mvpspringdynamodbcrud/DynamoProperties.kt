package com.seanmassie.mvpspringdynamodbcrud

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.*
import com.amazonaws.util.StringUtils
import org.slf4j.LoggerFactory
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean


@Configuration
@EnableDynamoDBRepositories(basePackageClasses = [UrlRepository::class])
class DynamoProperties {

    private val log = LoggerFactory.getLogger(this.javaClass)

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

        val tableRequest = DynamoDBMapper(amazonDynamoDB)
                .generateCreateTableRequest(ShortUrl::class.java)
                .withProvisionedThroughput(ProvisionedThroughput(1L, 1L))

        try {
            amazonDynamoDB.createTable(tableRequest)
           log.info("table created for ${ShortUrl::class}")
        }
        catch (exc : ResourceInUseException) {

            log.error("Table already exists for ${ShortUrl::class}")
        }
        return amazonDynamoDB
    }

    @Bean
    fun amazonAWSCredentials(): AWSCredentials {
        return BasicAWSCredentials(
                amazonAWSAccessKey!!, amazonAWSSecretKey!!)
    }
}