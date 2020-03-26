package com.matyila.photoshop.users.PhotoShopUsers.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.matyila.photoshop.users.PhotoShopUsers.model.AlbumResponseModel;

@FeignClient(url="http://localhost:8089", name="albums-ws")
public interface AlbumsServiceClient {
	
	@GetMapping(path="/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable("id") String id);

}

