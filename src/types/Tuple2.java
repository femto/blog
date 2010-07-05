package types;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class Tuple2<T1,T2> extends Tuple1<T1>{
	public T2 _2 = null;

	public Tuple2(T1 o1, T2 o2){
		super( o1);
		_2 = o2;
	}
}

