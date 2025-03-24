package com.sladamos.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ContourerDataFileLoaderTest {

    @Test
    void shouldThrowContourerLoaderExceptionWhenUnableToLoadData() {
        ContourerDataFileLoader uut = new ContourerDataFileLoader();

        assertThatThrownBy(uut::loadData).isInstanceOf(ContourerLoaderException.class);
    }

}