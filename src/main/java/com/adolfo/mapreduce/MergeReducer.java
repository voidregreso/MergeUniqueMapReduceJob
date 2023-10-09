package com.adolfo.mapreduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// Overload the reduce function here to copy the key from the input directly to the key of the output data
public class MergeReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Set<String> uniqueCourses = new HashSet<>();
        for (Text value : values) {
            uniqueCourses.add(value.toString());
        }
        for (String course : uniqueCourses) {
            context.write(key, new Text(course));
        }
    }
}
