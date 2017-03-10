package net.evaluation;

import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoField;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.ipfs.multihash.Multihash;
import io.ipfs.multihash.Multihash.Type;
import net.evaluation.model.Blockchain;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlockchainApplicationTests {

	@Test
	public void checkit() {
		Blockchain bc = Blockchain.init();
		bc.addBlock( bc.getLatestBlock().generateNextBlock(bc, "second block" ) );
		
		System.err.println( bc );
	}
	
	@Test
	@Ignore
	public void createGeneisBlockHash() throws Exception {
		String value = 0 + "0" + Instant.ofEpochSecond(1489073075) + "GenesisBlock";
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update( value.getBytes("UTF-8") );
		String hash58 = new Multihash(Type.sha2_256, md.digest()).toBase58();
		//produces QmSHDjpVJJwKvFwFjwcmmGBSFiKvSaCPW5H3JsKJ4DkA2y
	}
	
	@Test
	@Ignore
	public void contextLoads() {
		
		long value = Instant.now().getLong( ChronoField.INSTANT_SECONDS );
		System.err.println( value );
		
		Instant instant = Instant.ofEpochSecond( value );
		System.err.println( instant.getLong( ChronoField.INSTANT_SECONDS ) );
			
	}

}
