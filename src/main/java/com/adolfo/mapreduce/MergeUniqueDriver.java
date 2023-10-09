package com.adolfo.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;

public class MergeUniqueDriver { // Execute this scope, not the scope under main(){}

    private static final String mainNameNode = "hdfs://bigdata001:8020";
    private static final String resManager = "bigdata003";

    public static void main(String[] args) {

        // TODO Auto-generated method stub
        try {
            // Must specify or it will throw error
            UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");
            UserGroupInformation.setLoginUser(ugi);

            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", mainNameNode);
            // If you're going to pack this project into JAR, you need to configure this item
            conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
            conf.set("yarn.resourcemanager.hostname", resManager); // Must be added if the server is a cluster
            conf.set("mapreduce.app-submission.cross-platform","true");
            conf.set("mapreduce.framework.name", "yarn"); // Must be added if the server is a cluster
            conf.set("yarn.nodemanager.aux-services", "mapreduce_shuffle");

            Job job = Job.getInstance(conf, "RemovalOfDuplication");
            job.setJarByClass(MergeUniqueDriver.class);
            job.setMapperClass(MergeMapper.class);
            job.setReducerClass(MergeReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            String inputPath = "/user/tmp/input/";
            String outputPath = "/user/tmp/output/";

            FileInputFormat.addInputPath(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
