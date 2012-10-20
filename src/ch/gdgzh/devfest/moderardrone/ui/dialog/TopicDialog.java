package ch.gdgzh.devfest.moderardrone.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import ch.gdgzh.devfest.moderardrone.R;
import ch.gdgzh.devfest.moderardrone.ui.HostActivity;

public class TopicDialog extends DialogFragment implements DialogInterface.OnClickListener {

    public static final String TAG = "TopicDialog";

    private EditText topicEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_topic, null);
        topicEditText = (EditText) dialogView.findViewById(R.id.topic);
        return new AlertDialog.Builder(getActivity())
            .setTitle(getActivity().getString(R.string.dialog_topic_title))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok, this)
            .setNegativeButton(android.R.string.cancel, null)
            .create();
    }

    public static void show(FragmentManager fragmentManager) {
        TopicDialog dialog = new TopicDialog();
        dialog.show(fragmentManager, TAG);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                Intent hostIntent = new Intent(getActivity(), HostActivity.class);
                hostIntent.putExtra(HostActivity.EXTRA_TOPIC, topicEditText.getText().toString());
                startActivity(hostIntent);
                break;
            default:
                throw new IllegalArgumentException("Unknown button clicked: " + which);
        }
    }
}
