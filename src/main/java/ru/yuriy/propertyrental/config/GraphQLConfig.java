package ru.yuriy.propertyrental.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Configuration
public class GraphQLConfig
{
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);


    @Bean
    public GraphQLScalarType timestampScalar() {
        return GraphQLScalarType.newScalar()
                .name("Timestamp")
                .description("Custom scalar for java.sql.Timestamp")
                .coercing(new Coercing<Timestamp, String>() {

                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof Timestamp) {
                            return timestampFormat.format(dataFetcherResult);
                        }
                        throw new CoercingSerializeException("Unable to serialize object to Timestamp: " + dataFetcherResult);
                    }

                    @Override
                    public Timestamp parseValue(Object input) throws CoercingParseValueException {
                        if (input instanceof String) {
                            try {
                                return new Timestamp(timestampFormat.parse(input.toString()).getTime());
                            } catch (ParseException e) {
                                throw new CoercingParseValueException("Invalid value for Timestamp: " + input, e);
                            }
                        }
                        throw new CoercingParseValueException("Expected a String input for Timestamp, but got: " + input);
                    }

                    @Override
                    public Timestamp parseLiteral(Object input, Map<String, Object> variables) throws CoercingParseLiteralException {
                        if (input instanceof String) {
                            try {
                                return new Timestamp(timestampFormat.parse((String) input).getTime());
                            } catch (ParseException e) {
                                throw new CoercingParseLiteralException("Invalid literal for Timestamp: " + input, e);
                            }
                        }
                        throw new CoercingParseLiteralException("Expected a String literal for Timestamp, but got: " + input);
                    }
                })
                .build();
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(GraphQLScalarType timestampScalar)
    {
        return wiringBuilder -> wiringBuilder
                .scalar(timestampScalar);
    }

    @Bean
    public RuntimeWiringConfigurer dateScalar()
    {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.Date);
    }
}