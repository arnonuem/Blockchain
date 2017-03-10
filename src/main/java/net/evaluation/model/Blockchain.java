package net.evaluation.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import net.evaluation.model.exception.InvalidBlockException;

/**
 * <ul>
 * 	<li>init blockchain with Genesisblock</li>
 *  <li>is blockchain initialized</li>
 * 	<li>get latest block</li>
 *  <li>add block to blockchain if valid</li>
 * </ul>
 * 
 */
public class Blockchain {

	//TODO persist Blockchain with mapdb
	private List<Block> chain = new ArrayList<>();
	
	private Blockchain() {}
	
	public static Blockchain init() {
		Blockchain bc = new Blockchain();
		bc.chain.add( createGenesisBlock() );
		return bc;
	}
	
	private static Block createGenesisBlock() {
		return new Block( 0, "0", Instant.ofEpochSecond(1489073075), "GenesisBlock", "QmSHDjpVJJwKvFwFjwcmmGBSFiKvSaCPW5H3JsKJ4DkA2y" );
	}
	
	public Block getLatestBlock() {
		return chain.get( chain.size() - 1 );
	}
	
	public void addBlock( Block block ) {
		if( isNewBlockValid( block, getLatestBlock() ) )
			chain.add( block );
	}
	
	private boolean isNewBlockValid( Block newBlock, Block previousBlock ) {
		if( previousBlock.index + 1 != newBlock.index ) {
			throw new InvalidBlockException( "Index of the new block is expected to be incremented by one compared to index of the given previous block." );
		} else if( !previousBlock.hash.equals( newBlock.previousHash ) ) {
			throw new InvalidBlockException( "Hash of the new block is expected to be the same as the previousHash of the given previous block." );
		} else if( !newBlock.calculateHashForBlock( newBlock ).equals( newBlock.hash ) ) {
			throw new InvalidBlockException( "Hash of the new block is invalid." );
		}
		return true;
	}
	
	//TODO replaceChain
	
	//TODO isValidChain
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( Block b : chain ) {
			sb.append( b.hash );
			sb.append( "\n" );
		}
		return sb.toString();
	}
	
}
