package com.damoo.samples;

import org.junit.Test;

import com.damoo.samples.models.QueueObj;
import com.microsoft.azure.functions.*;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * Unit test for Service Bus Functions.
 */
public class ServiceBusFuncTests {
    /**
     * Test output is a copy of the input.
     */
    @Test
    public void outputIsCopyOfInput() throws Exception {
        // arrange
        QueueObj input = new QueueObj("Test1", "Bananas");

        ServiceBusProducerFunc func = new ServiceBusProducerFunc();
        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();
        
        OutputBinding<QueueObj> output = new OutputBinding<QueueObj>(){        
            private QueueObj val;
            @Override
            public void setValue(QueueObj value) {
                this.val = value;
            }        
            @Override
            public QueueObj getValue() {
                return val;
            }
        };

        // mock http req with custom object
        final HttpRequestMessage<Optional<QueueObj>> req = mock(HttpRequestMessage.class);
        final Optional<QueueObj> queryBody = Optional.of(input);
        doReturn(queryBody).when(req).getBody();

        // act
        func.run(req, output, context);

        // assert
        assertEquals("Test1", output.getValue().name);      
    }

    /**
     * Test queue 1 copies to queue 2
     */
    @Test
    public void queue1CopiesToQueue2() throws Exception {
        // arrange
        QueueObj input = new QueueObj("Test1", "Bananas");

        ServiceBusConsumerFunc func = new ServiceBusConsumerFunc();
        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();
        
        OutputBinding<QueueObj> output = new OutputBinding<QueueObj>(){        
            private QueueObj val;
            @Override
            public void setValue(QueueObj value) {
                this.val = value;
            }        
            @Override
            public QueueObj getValue() {
                return val;
            }
        };

        // act
        func.run(input, output, context);

        // assert
        assertEquals("Test1", output.getValue().name);      
    }
}
