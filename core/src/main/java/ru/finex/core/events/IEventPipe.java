package ru.finex.core.events;

/**
 *
 * @author m0nster.mind
 * @date 23.03.2018
 */
public interface IEventPipe<Input, Output> {

	Output process(Input object);

}
