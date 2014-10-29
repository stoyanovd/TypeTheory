package dima.vertexes;

/**
 * Created  by dima  on 26.10.14.
 */
public class NiceVertex extends Vertex {

	NiceVertex(Vertex v) {
		NamesDecorator namesDecorator = new NamesDecorator(v);
		Vertex t = namesDecorator.getNewNice();
		left = t.left;
		right = t.right;
		propose = t.propose;
		args = t.args;
		operation = t.operation;
		countHashAndCo(false);
	}
}
