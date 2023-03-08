package jelte.mygame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	Code code;

	enum Code {
		TEST
	}
}
