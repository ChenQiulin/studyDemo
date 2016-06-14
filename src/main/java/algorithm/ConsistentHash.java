package algorithm;

import java.util.*;

public class ConsistentHash<T> {

    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();

    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
                          Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        Long hash = hashFunction.hash(key.toString());
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public static void main(String[] args) {
        String [] hostArr = {"host1","host2","host3"};
        List<String> nodes = Arrays.asList(hostArr);
        int numberOfReplicas = 10;
        ConsistentHash<String> consistentHash = new ConsistentHash<String>(new HashFunction(),numberOfReplicas,nodes);
        Map<String,Set<Integer>> cacheMap= new HashMap<String, Set<Integer>>();
        int keyNum = 1000;
        for(int i=0;i<keyNum;i++){
            Set<Integer> set = consistentHash.get(keyNum+"")==null? new HashSet<Integer>():cacheMap.get(consistentHash.get(keyNum+""));
            set.add(keyNum);
        }

        System.out.println("*************************** 3个cache  ***************************************");



        System.out.println("***************************  4个cache  ***************************************");


        System.out.println("***************************  5个cache  ***************************************");


        System.out.println("***************************  6个cache  ***************************************");


    }

}