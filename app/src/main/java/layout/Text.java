package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nakul.projectone.R;
public class Text extends Fragment {
    Button save;
    EditText homeloc,name,homerad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_text, container, false);
        save=(Button)view.findViewById(R.id.save);
        homeloc=(EditText)view.findViewById(R.id.homeloc);
        homerad=(EditText)view.findViewById(R.id.rad);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String home=homeloc.getText().toString();
                String radius=homerad.getText().toString();
                Bundle args = new Bundle();
                args.putString("HOME", home);
                args.putInt("Radius",Integer.valueOf(radius));
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                Fragment fr=new MapFragment();
                fr.setArguments(args);
                //ft.replace(R.id.content_frame, fr);
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Text View");
    }
}
