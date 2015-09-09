package fr.capverszero.recunit.aspect;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.DisposableBean;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import fr.capverszero.recunit.Mode;
import fr.capverszero.recunit.Record;

/**
 * @author niclange PointCut for AOP recunit
 */
public class RecUnitPointCut implements DisposableBean {

	private final static Log log = LogFactory.getLog(RecUnitPointCut.class);

	/**
	 * Test methode Id, key prefix
	 */
	private static final InheritableThreadLocal<String> identifier = new InheritableThreadLocal<String>();
	private static final InheritableThreadLocal<ConcurrentNavigableMap<String, String>> map = new InheritableThreadLocal<ConcurrentNavigableMap<String, String>>();

	public String getIdentifier() {
		return identifier.get();
	}

	public void setIdentifier(String value) {
		identifier.set(value);
		ConcurrentNavigableMap<String, String> localMap = db.treeMap(value);
		map.set(localMap);
	}

	@Getter
	private static ConcurrentMap<String, Integer> counters = new ConcurrentHashMap<String, Integer>();

	/**
	 * Mode : recoding et playing
	 */
	@Getter
	@Setter
	private Mode mode;

	/**
	 * working directory path ends by "\" or "/"
	 */
	@Getter
	private final String workingDirectory;

	private final DB db;

	/**
	 * Constructor
	 */
	public RecUnitPointCut(final String workingDirectory) {

		if (workingDirectory == null || workingDirectory.isEmpty()) {
			this.workingDirectory = System.getProperty("java.io.tmpdir");
		} else {
			this.workingDirectory = workingDirectory;
		}
		log.debug("workingDirectory : " + this.workingDirectory);

		File file = new File(workingDirectory + "recUnit.db");
		db = DBMaker.fileDB(file).closeOnJvmShutdown().make();

	}

	/**
	 * @throws Exception
	 */
	public void destroy() throws Exception {
		db.close();

	}

	public void initCounters() {
		counters = new ConcurrentHashMap<String, Integer>();
	}

	/**
	 * puplic jointpoint to AOP play and record actions
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	public Object run(ProceedingJoinPoint joinPoint) throws Throwable {
		switch (mode) {
		case Record:
			return records(joinPoint);
		case Play:
			return play(joinPoint);
		default:
			break;
		}
		return null;
	}

	/**
	 * method recording action
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	private Object records(ProceedingJoinPoint joinPoint) throws Throwable {

		Record record = new Record();
		String postKey = createPostKey(joinPoint, record);
		String key = null;
		Integer count = null;
		ConcurrentNavigableMap<String, String> localMap = map.get();
		synchronized (postKey.intern()) {
			count = counters.get(postKey);
			if (count == null) {
				count = 0;
			}
			// call methode and complete record add try catch to complete
			// recorde with throwable
			record.setResult(joinPoint.proceed());
			count++;
			counters.put(postKey, count);

			key = createKey(postKey);
		}

		// use JsonWriter because Json is cool format and it's serialize even if
		// it's not serializable
		String recordString = JsonWriter.toJson(record);
		localMap.put(key, recordString);
		db.commit();

		if (log.isDebugEnabled()) {
			log.debug(record.toString());
		}
		return record.getResult();
	}

	private Object play(ProceedingJoinPoint joinPoint) throws Throwable {

		Record record = new Record();
		String postKey = createPostKey(joinPoint, record);
		String json = null;
		String key = null;
		ConcurrentNavigableMap<String, String> localMap = map.get();
		if (log.isDebugEnabled()) {
			log.debug(postKey.toString());
		}

		synchronized (postKey.intern()) {
			Integer counter = counters.get(postKey);

			if (counter == null) {
				counter = 1;
				counters.put(postKey, counter);
			} else {
				counter++;
				counters.put(postKey, counter);
			}
			key = createKey(postKey);
			json = localMap.get(key);
		}
		if (json == null) {
			log.error("Record not fund in DB " + key);
			throw new RuntimeException("Record not fund in DB " + key);
		}
		// next record

		Object result = JsonReader.jsonToJava(json);
		return ((Record) result).getResult();
	}

	private String createPostKey(ProceedingJoinPoint joinPoint,
			final Record record) {
		String name = joinPoint.getSignature().toShortString();

		record.setMethodName(name);
		record.setArguments(Arrays.asList(joinPoint.getArgs()));
		String postkey = getIdentifier() + record.toPostfixKey();

		return postkey;
	}

	private String createKey(String postKey) {
		return getIdentifier() + "-" + postKey + "-" + counters.get(postKey);
	}

}
