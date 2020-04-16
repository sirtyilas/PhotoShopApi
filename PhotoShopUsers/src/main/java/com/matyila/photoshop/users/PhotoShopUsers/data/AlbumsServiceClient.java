package com.matyila.photoshop.users.PhotoShopUsers.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.matyila.photoshop.users.PhotoShopUsers.model.AlbumResponseModel;

@FeignClient(name="albums-ws", fallback = AlbumsServiceClient.AlbumsFallback.class)
public interface AlbumsServiceClient {
	
	@GetMapping(path="/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable("id") String id);

	@Component
	class AlbumsFallback implements AlbumsServiceClient{
		@Override
		public List<AlbumResponseModel> getAlbums(String id) {
			return new ArrayList<AlbumResponseModel>();
		}
	}
}

