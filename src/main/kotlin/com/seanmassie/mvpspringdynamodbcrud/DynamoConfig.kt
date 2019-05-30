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
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("amazon.aws")
@EnableDynamoDBRepositories(basePackageClasses = [UrlRepository::class])
class DynamoConfig {

    private val log = LoggerFactory.getLogger(this.javaClass)

    lateinit var endpoint:String
    lateinit var accessKey:String
    lateinit var secretKey:String

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        val amazonDynamoDB = AmazonDynamoDBClient(amazonAWSCredentials())

        if (!StringUtils.isNullOrEmpty(this.endpoint)) {
            amazonDynamoDB.setEndpoint(this.endpoint)
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
        return BasicAWSCredentials(this.accessKey, this.secretKey)
    }
}