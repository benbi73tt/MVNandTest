import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.madbrains.simpleList.ArrayIndexOutOfBoundsException;
import ru.madbrains.simpleList.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static ru.madbrains.impl.Program.print;

public class ListOperationTest {
    private Car octavia;
    private Car rapid;
    private Car karoq;
    private Car corolla;
    private Car camry;
    private Car superb;
    private Car kodiaq;
    private Car vesta;

    private SimpleList test;
    private SimpleList testNull;
    private SimpleList basicTest;


    @Before
    public void setUp() throws NoEntityException {
        testNull = new ListOperation();
        test = new ListOperation();
        octavia = new Car("Octavia", "2018", 1.7f);
        rapid = new Car("Rapid", "2015", 0.7f);
        karoq = new Car("Karoq", "2019", 2f);
        kodiaq = new Car("Kodiaq", "2020", 2.4f);
        superb = new Car("Superb", "2021", 3.1f);
        camry = new Car("Camry", "2015", 1f);
        corolla = new Car("Corolla", "2020", 1.5f);
        vesta = new Car("Vesta", "2021", 1f);

        test.add(rapid);
        test.add(octavia);
        test.add(karoq);
        test.add(kodiaq);
        test.add(superb);
        test.add(camry);
        test.add(corolla);

        basicTest = test;
    }

    public SimpleList createIndexList(int count) throws NoEntityException {
        SimpleList<Car> indexList = new ListOperation();
        for (int i = 0; i < count; i++) {
            indexList.add(new Car(String.valueOf(i), String.valueOf(count - 1 - i), (float) i));
        }
        return indexList;
    }

    @Test
    public void add() throws ArrayIndexOutOfBoundsException, NoEntityException {
        SimpleList test2 = new ListOperation();
        test2.add(octavia);
        test2.add(karoq);
        test2.add(rapid);

        List<Car> actual = new ArrayList<>();
        actual.add(octavia);
        actual.add(rapid);

        Assert.assertEquals(test2.get(0).get(), actual.get(0));
        Assert.assertEquals(test2.get(2).get(), actual.get(1));
    }

    @Test(expected = NoEntityException.class)
    public void addNull() throws NoEntityException {
        SimpleList test2 = new ListOperation();
        test2.add(null);
    }

    @Test
    public void insert() throws Exception {
        test.insert(5, camry);
        Assert.assertEquals(test.get(5).get(), camry);
    }

    @Test(expected = NoEntityException.class)
    public void insertNull() throws Exception {
        test.insert(3, null);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void insertOutIndexArray() throws Exception {
        test.insert(5000, new Car("test", "test", 73.27f));
    }

    @Test
    public void insertInBegin() throws Exception {
        testNull.insert(0, corolla);
        Assert.assertEquals(testNull.get(0).get(), corolla);
    }

    @Test
    public void remove() throws Exception {
        Object buf = test.get(6).get();
        test.remove(5);
        Assert.assertEquals(6, test.size());
        Assert.assertEquals(test.get(5).get(), buf);
    }

    @Test
    public void removeFromNullArray() throws Exception {
        testNull.add(camry);
        testNull.remove(0);
    }

    @Test
    public void get() throws Exception {
        for (int i = 0; i < test.size(); i++) {
            Assert.assertTrue(test.get(i) != Optional.empty());
        }
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(test.size(), 7);
        SimpleList test2 = new ListOperation();

        test2.add(karoq);
        test2.add(octavia);
        Assert.assertEquals(test2.size(), 2);

        test.addAll(test2);
        Assert.assertEquals(test.size(), 9);

        test.remove(3);
        Assert.assertEquals(test.size(), 8);

        test.insert(0, octavia);
        Assert.assertEquals(test.size(), 9);
    }

    @Test
    public void first() {
        Assert.assertTrue(test.first(vesta) == -1 ? true : false);
        Assert.assertEquals(test.first(camry), 5);
    }

    @Test
    public void last() {
        Assert.assertTrue(test.last(vesta) == -1 ? true : false);
        Assert.assertEquals(test.last(camry), 5);
    }

    @Test
    public void contains() {
        Assert.assertTrue(test.contains(vesta) == false ? true : false);
        Assert.assertTrue(test.contains(superb));
    }

    @Test
    public void addAll() throws NoEntityException, ArrayIndexOutOfBoundsException {
        SimpleList test2 = new ListOperation();
        test2.add(corolla);
        test2.add(octavia);
        test2.add(rapid);
        test2.add(kodiaq);

        int sizeTest2 = test2.size();
        test2.addAll(test);
        Assert.assertTrue((sizeTest2 + test.size()) == test2.size() ? true : false);

        for (int i = sizeTest2, j = 0; i < test2.size(); j++, i++) {
            Assert.assertTrue(test2.get(i).equals(test.get(j)));
        }
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue((test.size() > 0 && test.isEmpty()) ? true : false);
    }

    @Test
    public void shuffle() throws NoEntityException {
        SimpleList shufTest = test.shuffle();
        Assert.assertTrue((shufTest.size() == test.size()) ? true : false);
        Assert.assertNotEquals(shufTest, test);
        Assert.assertEquals(test, basicTest);
    }

    @Test
    public void shuffleIndexList() throws NoEntityException, ArrayIndexOutOfBoundsException {
        SimpleList indexList = createIndexList(1000);
        SimpleList shufTest = indexList.shuffle();
        Assert.assertNotEquals(shufTest, indexList);
        for (int i = 0; i < shufTest.size(); i++) {
            Assert.assertNotEquals(shufTest.get(i), indexList.get(i));
        }
    }

    @Test
    public void sortIndexListFloatPrice() throws NoEntityException, ArrayIndexOutOfBoundsException, ExecutionException, InterruptedException {
        SimpleList indexList = createIndexList(1000);

        Comparator<Car> comparatorPrice = Comparator.comparing(Car::getPrice);
        SimpleList<Car> sortTestPrice = indexList.sort(comparatorPrice);
        Assert.assertNotEquals(sortTestPrice, indexList);

        for (int i = 0, j = 1; i < sortTestPrice.size() - 1; i++, j++) {
            Assert.assertTrue(0 < (sortTestPrice
                    .get(j).map(x -> x.getPrice()).get()) - (sortTestPrice
                    .get(i).map(x -> x.getPrice()).get()));
        }
    }

    @Test
    public void sortStringAge() throws NoEntityException, ArrayIndexOutOfBoundsException, ExecutionException, InterruptedException {
        SimpleList sortAge = new ListOperation();

        sortAge.add(camry);
        sortAge.add(rapid);
        sortAge.add(octavia);
        sortAge.add(karoq);
        sortAge.add(corolla);

        Comparator<Car> comparatorAge = Comparator.comparing(Car::getAge);
        SimpleList sortTestAge = test.sort(comparatorAge);
        Assert.assertEquals(test, basicTest);
        Assert.assertNotEquals(sortTestAge, test);

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(sortAge.get(i), sortTestAge.get(i));
        }
    }

    @Test
    public void sortStringName() throws NoEntityException, ArrayIndexOutOfBoundsException, ExecutionException, InterruptedException {
        SimpleList sortName = new ListOperation();

        sortName.add(camry);
        sortName.add(corolla);
        sortName.add(karoq);
        sortName.add(kodiaq);
        sortName.add(octavia);

        Comparator<Car> comparatorName = Comparator.comparing(Car::getName);
        SimpleList sortTestName = test.sort(comparatorName);
        Assert.assertEquals(test, basicTest);
        Assert.assertNotEquals(sortTestName, test);

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(sortName.get(i), sortTestName.get(i));
        }
    }

    @Test
    public void sortFloatPrice() throws NoEntityException, ArrayIndexOutOfBoundsException, ExecutionException, InterruptedException {
        SimpleList sortPrice = new ListOperation();

        sortPrice.add(rapid);
        sortPrice.add(camry);
        sortPrice.add(corolla);
        sortPrice.add(octavia);
        sortPrice.add(karoq);

        Comparator<Car> comparatorPrice = Comparator.comparing(Car::getPrice);
        SimpleList sortTestPrice = test.sort(comparatorPrice);
        Assert.assertEquals(test, basicTest);
        Assert.assertNotEquals(sortTestPrice, test);

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(sortPrice.get(i), sortTestPrice.get(i));
        }
    }

    @Test
    public void timeBubbleSort() throws NoEntityException, ExecutionException, InterruptedException, ArrayIndexOutOfBoundsException {
        Comparator<Car> comparatorName = Comparator.comparing(Car::getName);
        SimpleList mycar = createIndexList(3000000);
        mycar = mycar.shuffle();

        SimpleList youcar = createIndexList(15000);
        youcar = youcar.shuffle();


        long start = System.nanoTime();
        mycar = mycar.sort(comparatorName);
        long finish = System.nanoTime();

        long start1 = System.nanoTime();
        youcar = youcar.sortingThreads(comparatorName);
        long finish1 = System.nanoTime();//        print(youcar);

        long elapsed = finish - start;
        long elapsed1 = finish1 - start1;

        System.out.println("Mycar Время в мс: " + elapsed / 100000);
        System.out.println("Youcar Время в мс: " + elapsed1 / 100000);
    }
}


