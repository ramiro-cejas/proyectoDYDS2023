package controller;

public class ControllerHistoryHandler {
    private final ControllerVideoGameWiki controllerVideoGameWiki;

    public ControllerHistoryHandler(ControllerVideoGameWiki controllerVideoGameWiki) {
        this.controllerVideoGameWiki = controllerVideoGameWiki;
    }

    public void onEventSaveSearched(String resultBody) {
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().saveSearchedResult(resultBody);
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }
}