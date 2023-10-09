package com.adolfo.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// Overload the map function here to copy the value from the input directly to the key of the output data.
// Note that an exception is thrown in the map method
public class MergeMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] tokens = line.split("\\s+");
        if (tokens.length == 2) {
            String studentId = tokens[0];
            String course = tokens[1];
            context.write(new Text(studentId), new Text(course));
        }
    }
}
