package com.seanmassie.mvpspringdynamodbcrud

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository


@EnableScan
interface UrlRepository : CrudRepository<ShortUrl, String> {

}