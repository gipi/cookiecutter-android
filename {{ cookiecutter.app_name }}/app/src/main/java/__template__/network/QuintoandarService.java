package {{ cookiecutter.package_name }}.network;

import {{ cookiecutter.package_name }}.envelope.DataEnvelope;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface QuintoandarService {

    @GET("/{id}")
    Observable<DataEnvelope> fetchData(@Path("id") Long id);
}
