package com.seanmassie.mvpspringdynamodbcrud

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UrlController(val repo : UrlRepository) {
    @GetMapping("/")
    fun returnUrls() = repo.findById("blahblah")

    @PostMapping("/")
    fun createUrl(@RequestBody url : ShortUrl) = repo.save(url)
}