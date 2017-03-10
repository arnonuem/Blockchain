package net.evaluation.model;

import java.security.MessageDigest;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.ipfs.multihash.Multihash;
import io.ipfs.multihash.Multihash.Type;
import net.evaluation.model.exception.HashEncodingFailedException;

@Component
@Scope("prototype")
public class Block {

	// TODO multihash

	public final long index;
	public final String previousHash;
	public final Instant timestamp;
	public final Object data;
	public final String hash;

	@Autowired
	public Block( long index, String previousHash, Instant timestamp, Object data, String hash) {
		this.index = index;
		this.previousHash = previousHash;
		this.timestamp = timestamp;
		this.data = data;
		this.hash = hash;
	}
	
	
	public Block generateNextBlock( Blockchain blockchain, Object data ) {
		Block latestBlock = blockchain.getLatestBlock();
		long nextIndex = latestBlock.index + 1;
		Instant nextTimestamp = Instant.now();
		String nextHash = calculateHash( nextIndex, latestBlock.hash, nextTimestamp, data );
		return new Block( nextIndex, latestBlock.hash, nextTimestamp, data, nextHash );
	}

	public String calculateHashForBlock( Block block ) {
		return calculateHash( block.index, block.previousHash, block.timestamp, block.data );	
	}
	
	
	/**
	 * Produces Multihash String Base58
	 */
	private String calculateHash( long index, String previousHash, Instant timestamp, Object data ) throws HashEncodingFailedException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String value = index + previousHash + timestamp + data;
			md.update( value.getBytes("UTF-8") );
			return new Multihash( Type.sha2_256, md.digest() ).toBase58();
		} catch( Exception e) {
			throw new HashEncodingFailedException( e );
		}
	}
}
