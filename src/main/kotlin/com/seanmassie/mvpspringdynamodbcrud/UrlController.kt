package com.seanmassie.mvpspringdynamodbcrud

import org.springframework.web.bind.annotation.*

@RestController
class UrlController(val repo : UrlRepository) {
    @GetMapping("/{id}")
    fun returnUrls(@PathVariable("id") id : String) = repo.findById(id)

    @PostMapping("/")
    fun createUrl(@RequestBody url : ShortUrl) = repo.save(url)
}