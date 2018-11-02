package br.com.fza.paymentchallenge.converter;

@FunctionalInterface
public interface Converter<F, T> {

    T convert(F from) ;

}