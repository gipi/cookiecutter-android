package br.com.quintoandar.template.network;

import br.com.quintoandar.template.envelope.DataEnvelope;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface QuintoandarService {

    @GET("/{id}")
    Observable<DataEnvelope> fetchData(@Path("id") Long id);
}
