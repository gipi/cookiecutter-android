package com.company.templateapplication.network;

import com.google.common.collect.ArrayListMultimap;
import com.orhanobut.hawk.Hawk;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class OrganizationCookieStore implements CookieStore {

    private final static String TAG = OrganizationCookieStore.class.getSimpleName();

    @Override
    public void add(URI uri, HttpCookie httpCookie) {
        ArrayListMultimap<URI, HttpCookie> cookies = Hawk.get(TAG, ArrayListMultimap.create());
        cookies.put(uri, httpCookie);
        Hawk.put(TAG, cookies);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return Hawk.get(TAG, ArrayListMultimap.<URI, HttpCookie>create()).get(uri);
    }

    @Override
    public List<HttpCookie> getCookies() {
        return new ArrayList<>(Hawk.get(TAG, ArrayListMultimap.<URI, HttpCookie>create()).values());
    }

    @Override
    public List<URI> getURIs() {
        return new ArrayList<>(Hawk.get(TAG, ArrayListMultimap.<URI, HttpCookie>create()).keys());
    }

    @Override
    public boolean remove(URI uri, HttpCookie httpCookie) {
        ArrayListMultimap<URI, HttpCookie> cookies = Hawk.get(TAG, ArrayListMultimap.create());
        cookies.remove(uri, httpCookie);
        return Hawk.put(TAG, cookies);
    }

    @Override
    public boolean removeAll() {
        return Hawk.remove(TAG);
    }
}
