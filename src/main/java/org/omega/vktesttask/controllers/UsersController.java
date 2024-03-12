package org.omega.vktesttask.controllers;

import lombok.RequiredArgsConstructor;
import org.omega.vktesttask.aspect.Audit;
import org.omega.vktesttask.service.JsonPlaceholderService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@CrossOrigin(originPatterns = "**")
public class UsersController {
    private final JsonPlaceholderService jsonPlaceholderService;
    private final String resource = "users";

    @Audit
    @Cacheable(value = "usersCacheAll")
    @GetMapping({"", "/"})
    public ResponseEntity<?> getAll() {
        return jsonPlaceholderService.getRequest(resource);
    }

    @Audit
    @Cacheable(value = "usersCacheById")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return jsonPlaceholderService.getRequest(resource + "/" + id);
    }

    @Audit
    @Cacheable(value = "usersCacheAlbums")
    @GetMapping("/{id}/albums")
    public ResponseEntity<?> getByIdAlbums(@PathVariable int id) {
        return jsonPlaceholderService.getRequest(resource + "/" + id + "/albums");
    }

    @Audit
    @Cacheable(value = "usersCacheTodos")
    @GetMapping("/{id}/todos")
    public ResponseEntity<?> getByIdTodos(@PathVariable int id) {
        return jsonPlaceholderService.getRequest(resource + "/" + id + "/todos");
    }

    @Audit
    @Cacheable(value = "usersCachePosts")
    @GetMapping("/{id}/posts")
    public ResponseEntity<?> getByIdPosts(@PathVariable int id) {
        return jsonPlaceholderService.getRequest(resource + "/" + id + "/posts");
    }


    @Audit
    @Cacheable(value = "usersCachePut")
    @PutMapping("/{id}")
    public ResponseEntity<?> usersPut(@PathVariable int id, @RequestBody String user) {
        return jsonPlaceholderService.putRequest(resource + "/" + id , user);
    }

    @Audit
    @Cacheable(value = "usersCachePost")
    @PostMapping({"", "/"})
    public ResponseEntity<?> usersPost(@RequestBody String users) {
        return jsonPlaceholderService.postRequest(resource, users);
    }

    @Audit
    @Cacheable(value = "usersCacheDelete")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable int id) {
        return jsonPlaceholderService.deleteRequest(resource + "/" + id);
    }
}
