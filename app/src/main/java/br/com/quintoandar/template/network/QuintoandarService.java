package br.com.quintoandar.template.network;

import android.app.Application;
import android.content.Context;

import br.com.quintoandar.template.BuildConfig;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.networkstate.NetworkStateChecker;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.retrofit.GsonRetrofitObjectPersisterFactory;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QuintoandarService extends SpiceService {

    public final static String BASE_URL = BuildConfig.ENDPOINT;

    protected List<Class<?>> retrofitInterfaceList = new ArrayList<>();

    private Map<Class<?>, Object> retrofitInterfaceToServiceMap = new HashMap<>();

    private RestAdapter.Builder builder;

    private RestAdapter restAdapter;

    private Converter converter;

    @Override
    public void onCreate() {
        super.onCreate();
        builder = createRestAdapterBuilder();
        restAdapter = builder.build();

        addRetrofitInterface(Quintoandar.class);
    }

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new GsonRetrofitObjectPersisterFactory(application, getConverter(), getCacheFolder()));
        return cacheManager;
    }

    @Override
    public int getThreadCount() {
        return 4;
    }

    protected String getServerUrl() {
        return BASE_URL;
    }

    public File getCacheFolder() {
        return new File(getCacheDir(), "cache");
    }

    protected RestAdapter.Builder createRestAdapterBuilder() {
        CookieHandler.setDefault(new CookieManager(new QuintoandarCookieStore(), CookiePolicy.ACCEPT_ALL));

        OkHttpClient httpClient = new OkHttpClient();
        return new RestAdapter.Builder()
                .setClient(new OkClient(httpClient))
                .setEndpoint(getServerUrl())
                .setRequestInterceptor(new QuintoandarRequestInterceptor())
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .setConverter(getConverter());
    }

    protected Converter createConverter() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.serializeNulls();
        return new GsonConverter(builder.create());
    }

    protected Converter getConverter() {
        if (converter == null) {
            converter = createConverter();
        }
        return converter;
    }

    protected <T> T getRetrofitService(Class<T> serviceClass) {
        T service = (T) retrofitInterfaceToServiceMap.get(serviceClass);
        if (service == null) {
            service = restAdapter.create(serviceClass);
            retrofitInterfaceToServiceMap.put(serviceClass, service);
        }
        return service;
    }

    @Override
    public void addRequest(CachedSpiceRequest<?> request, Set<RequestListener<?>> listRequestListener) {
        if (request.getSpiceRequest() instanceof RetrofitSpiceRequest) {
            RetrofitSpiceRequest retrofitSpiceRequest = (RetrofitSpiceRequest) request.getSpiceRequest();
            retrofitSpiceRequest.setService(getRetrofitService(retrofitSpiceRequest.getRetrofitedInterfaceClass()));
        }
        super.addRequest(request, listRequestListener);
    }

    @Override
    protected NetworkStateChecker getNetworkStateChecker() {
        return new NetworkStateChecker() {

            @Override
            public boolean isNetworkAvailable( Context context ) {
                return true;
            }

            @Override
            public void checkPermissions( Context context ) {

            }
        };
    }

    public final List<Class<?>> getRetrofitInterfaceList() {
        return retrofitInterfaceList;
    }

    protected void addRetrofitInterface(Class<?> serviceClass) {
        retrofitInterfaceList.add(serviceClass);
    }
}
