import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class SuperTable {

    public static void main(String[] args) throws IOException {

        // Instantiate Configuration class
        Configuration conf = HBaseConfiguration.create();
        conf.set("zookeeper.znode.parent", "/hbase-unsecure");

        try (Connection connection = ConnectionFactory.createConnection(conf)) {

            // Instaniate HBaseAdmin class
            Admin admin = connection.getAdmin();

            // Instantiating table descriptor class
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("powers"));

            // Adding column families to table descriptor
            tableDescriptor.addFamily(new HColumnDescriptor("personal"));
            tableDescriptor.addFamily(new HColumnDescriptor("professional"));

            // Execute the table through admin
            admin.createTable(tableDescriptor);
            System.out.println(" Table created ");
            //admin.close();

            // Instantiating HTable class
            Table hTable = connection.getTable(TableName.valueOf("powers"));

            // Instantiating Put class
            // accepts a row name.
            Put p = new Put(Bytes.toBytes("row1"));

            // adding values using add() method
            // accepts column family name, qualifier/row name ,value
            p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("hero"), Bytes.toBytes("superman"));
            p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("power"), Bytes.toBytes("strength"));
            p.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("name"), Bytes.toBytes("clark"));
            p.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("xp"), Bytes.toBytes("100"));

            // Saving the put Instance to the HTable.
            hTable.put(p);

            p = new Put(Bytes.toBytes("row2"));
            p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("hero"), Bytes.toBytes("batman"));
            p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("power"), Bytes.toBytes("money"));
            p.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("name"), Bytes.toBytes("bruce"));
            p.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("xp"), Bytes.toBytes("50"));
            hTable.put(p);

            p = new Put(Bytes.toBytes("row3"));
            p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("hero"), Bytes.toBytes("wolverine"));
            p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("power"), Bytes.toBytes("healing"));
            p.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("name"), Bytes.toBytes("logan"));
            p.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("xp"), Bytes.toBytes("75"));
            hTable.put(p);

            System.out.println("data inserted");

            // Instantiate the Scan class
            Scan scan = new Scan();

            // Scan the required columns
            scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("hero"));

            // Get the scan result
            try (ResultScanner scanner = hTable.getScanner(scan)) {

                // Reading values from scan result
                for (Result result = scanner.next(); result != null; result = scanner.next())
                    System.out.println("Found row : " + result);
            }

            // Instantiating Get class
            Get g = new Get(Bytes.toBytes("row1"));

            // Reading the data
            Result result = hTable.get(g);

            // Reading values from Result class object
            byte [] value = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("hero"));
            byte [] value1 = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("power"));

            // Printing the values
            String name = Bytes.toString(value);
            String city = Bytes.toString(value1);

            System.out.println("name: " + name + " city: " + city);

            // Htable closer
            hTable.close();
        }
    }
}

