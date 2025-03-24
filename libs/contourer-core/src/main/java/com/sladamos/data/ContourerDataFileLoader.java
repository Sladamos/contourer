package com.sladamos.data;

public class ContourerDataFileLoader implements ContourerDataLoader {

    @Override
    public ContourerData loadData() {
        throw new ContourerLoaderException();
    }
}
