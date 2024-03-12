package org.omega.vktesttask.controllers;

import lombok.RequiredArgsConstructor;
import org.omega.vktesttask.aspect.Audit;
import org.omega.vktesttask.service.JsonPlaceholderService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(originPatterns = "**")
public class PostsController {
    private final JsonPlaceholderService jsonPlaceholderService;
    private final String resource = "/posts";

    @Audit
    @Cacheable(value = "postsCacheAll")
    @GetMapping({"", "/"})
    public ResponseEntity<?> getAll() {
        return jsonPlaceholderService.getRequest(resource);
    }

    @Audit
    @Cacheable(value = "postsCacheById")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return jsonPlaceholderService.getRequest(resource + "/" + id);
    }

    @Audit
    @Cacheable(value = "postsCacheComments")
    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getByIdComments(@PathVariable int id) {
        return jsonPlaceholderService.getRequest(resource + "/" + id + "/comments");
    }

    @Audit
    @Cacheable(value = "postsCachePost")
    @PostMapping({"", "/"})
    public ResponseEntity<?> postsPost(@RequestBody String post) {

        return jsonPlaceholderService.postRequest(resource, post);
    }

    @Audit
    @Cacheable(value = "postsCachePut")
    @PutMapping("/{id}")
    public ResponseEntity<?> postPut(@PathVariable int id, @RequestBody String post) {
        return jsonPlaceholderService.putRequest(resource + "/" + id , post);
    }


    @Audit
    @Cacheable(value = "postsCacheDelete")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        return jsonPlaceholderService.deleteRequest(resource + "/" + id);
    }
}
