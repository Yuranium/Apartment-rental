package ru.yuriy.propertyrental.config;

import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Configuration
public class GraphQLConfig
{
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Bean
    public GraphQLScalarType timestampScalar()
    {
        return GraphQLScalarType.newScalar()
                .name("Timestamp")
                .description("Custom scalar for java.sql.Timestamp")
                .coercing(new Coercing<Timestamp, String>()
                {

                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException
                    {
                        if (dataFetcherResult instanceof Timestamp)
                            return timestampFormat.format(dataFetcherResult);
                        throw new CoercingSerializeException("Unable to serialize object to Timestamp: " + dataFetcherResult);
                    }

                    @Override
                    public Timestamp parseValue(Object input) throws CoercingParseValueException
                    {
                        if (input instanceof String)
                            try {
                                return new Timestamp(timestampFormat.parse(input.toString()).getTime());
                            } catch (ParseException e) {
                                throw new CoercingParseValueException("Invalid value for Timestamp: " + input, e);
                            }
                        throw new CoercingParseValueException("Expected a String input for Timestamp, but got: " + input);
                    }

                    @Override
                    public Timestamp parseLiteral(Object input, Map<String, Object> variables) throws CoercingParseLiteralException
                    {
                        if (input instanceof String)
                            try {
                                return new Timestamp(timestampFormat.parse((String) input).getTime());
                            } catch (ParseException e) {
                                throw new CoercingParseLiteralException("Invalid literal for Timestamp: " + input, e);
                            }
                        throw new CoercingParseLiteralException("Expected a String literal for Timestamp, but got: " + input);
                    }
                })
                .build();
    }

    @Bean
    public GraphQLScalarType dateScalar()
    {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Custom scalar for java.util.Date")
                .coercing(new Coercing<Date, String>()
                {

                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException
                    {
                        if (dataFetcherResult instanceof Date)
                            return dateFormat.format((Date) dataFetcherResult);
                        throw new CoercingSerializeException("Unable to serialize object as Date: " + dataFetcherResult);
                    }

                    @Override
                    public Date parseValue(Object input) throws CoercingParseValueException
                    {
                        try {
                            return dateFormat.parse(input.toString());
                        } catch (ParseException e) {
                            throw new CoercingParseValueException("Invalid value for Date: " + input, e);
                        }
                    }

                    @Override
                    public Date parseLiteral(Object input, Map<String, Object> variables) throws CoercingParseLiteralException
                    {
                        if (input instanceof String)
                            try {
                                return dateFormat.parse((String) input);
                            } catch (ParseException e) {
                                throw new CoercingParseLiteralException("Invalid literal for Date: " + input, e);
                            }
                        throw new CoercingParseLiteralException("Expected a String literal for Date, but got: " + input);
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
    public RuntimeWiringConfigurer runtimeWiringConfigurerDate(GraphQLScalarType dateScalar)
    {
        return wiringBuilder -> wiringBuilder
                .scalar(dateScalar);
    }
}