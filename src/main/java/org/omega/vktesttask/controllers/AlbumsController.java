package org.omega.vktesttask.controllers;

import lombok.RequiredArgsConstructor;
import org.omega.vktesttask.aspect.Audit;
import org.omega.vktesttask.service.JsonPlaceholderService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/albums")
@CrossOrigin(originPatterns = "**")
public class AlbumsController {
    private final JsonPlaceholderService jsonPlaceholderService;
    private final String resource = "/albums";


    @Audit
    @Cacheable(value = "albumsCacheAll")
    @GetMapping({"", "/"})
    public ResponseEntity<?> getAll() {
        return jsonPlaceholderService.getRequest(resource);
    }

    @Audit
    @Cacheable(value = "albumsCacheById")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return jsonPlaceholderService.getRequest(resource + "/" + id);
    }

    @Audit
    @Cacheable(value = "albumsCachePhotos")
    @GetMapping("/{id}/photos")
    public ResponseEntity<?> getByIdPhotos(@PathVariable int id) {
        return jsonPlaceholderService.getRequest(resource + "/" + id + "/photos");
    }

    @Audit
    @Cacheable(value = "albumsCachePost")
    @PostMapping({"", "/"})
    public ResponseEntity<?> albumsPost(@RequestBody String album) {

        return jsonPlaceholderService.postRequest(resource, album);
    }

    @Audit
    @Cacheable(value = "albumsCachePut")
    @PutMapping("/{id}")
    public ResponseEntity<?> albumsPut(@PathVariable int id, @RequestBody String album) {
        return jsonPlaceholderService.putRequest(resource + "/" + id , album);
    }


    @Audit
    @Cacheable(value = "albumsCacheDelete")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable int id) {
        return jsonPlaceholderService.deleteRequest(resource + "/" + id);
    }
}
