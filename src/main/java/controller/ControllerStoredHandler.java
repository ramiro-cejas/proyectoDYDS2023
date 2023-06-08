package controller;

public class ControllerStoredHandler {
    private final ControllerVideoGameWikiInterface controllerVideoGameWiki;

    public ControllerStoredHandler(ControllerVideoGameWikiInterface controllerVideoGameWiki) {
        this.controllerVideoGameWiki = controllerVideoGameWiki;
    }

    public void onEventDeleteStoredResult(String titleStored) {
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().deleteStoredResult(titleStored);
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }

    public void onEventUpdateStoredResult(String titleToUpdate, String bodyToUpdate) {
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().updateStoredResult(titleToUpdate, bodyToUpdate);
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }
}