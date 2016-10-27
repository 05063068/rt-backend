package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import com.rottentomatoes.movieapi.domain.model.VideoClip;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

@Component
public class VideoClipRepository extends AbstractRepository implements ResourceRepository<VideoClip, String> {

    @Override
    public void delete(String s) {
    }

    @Override
    public <S extends VideoClip> S save(S arg0) {
        return null;
    }

    @Override
    public VideoClip findOne(String videoClipId, RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        VideoClip videoClip = (VideoClip) emsClient.callEmsEntity(new HashMap<String,Object>(), "videoclip", videoClipId, VideoClip.class);
        return videoClip;
    }

    @Override
    public Iterable<VideoClip> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<VideoClip> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
