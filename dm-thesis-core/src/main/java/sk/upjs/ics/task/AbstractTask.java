package sk.upjs.ics.task;

public abstract class AbstractTask<S> implements Task<S> {

    protected abstract void preprocessInput();

    protected abstract S coreExecute();

    @Override
    public S execute() {
        preprocessInput();
        return coreExecute();
    }
}
