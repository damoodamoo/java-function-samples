package com.damoo.samples;

import org.junit.Test;

import com.damoo.samples.models.CosmosObj;
import com.microsoft.azure.functions.*;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * Unit test for Cosmos Functions.
 */
public class CosmosFuncTests {
    /**
     * Test output is a copy of the input, with correct updates
     */
    @Test
    public void outputIsCopyOfInput() throws Exception {
        // arrange
        CosmosObj[] input = new CosmosObj[3];
        input[0] = new CosmosObj("Test1", "Apples");
        input[1] = new CosmosObj("Test2", "Pears");
        input[2] = new CosmosObj("Test3", "Bananas");

        CosmosFunc func = new CosmosFunc();
        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();
        
        OutputBinding<CosmosObj[]> output = new OutputBinding<CosmosObj[]>(){        
            private CosmosObj[] val;
            @Override
            public void setValue(CosmosObj[] value) {
                this.val = value;
            }        
            @Override
            public CosmosObj[] getValue() {
                return val;
            }
        };

        // act
        func.run(input, output, context);

        // assert
        assertEquals("Test1--UPDATED", output.getValue()[0].name);
        assertEquals("Test2--UPDATED", output.getValue()[1].name);
        assertEquals("Test3--UPDATED", output.getValue()[2].name);       
    }
}
