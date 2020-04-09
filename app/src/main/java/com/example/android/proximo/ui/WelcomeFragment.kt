package com.example.android.proximo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.proximo.R

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {
    private val ARG_OBJECT = "object"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val titleTextView: TextView = view.findViewById(R.id.titleText)
            val descriptionTextView : TextView = view.findViewById(R.id.descriptionText)
            val img : ImageView = view.findViewById(R.id.img)

            val pos = getInt(ARG_OBJECT)

            if (pos == 0){
                img.setImageResource(R.drawable.viewpager_img_1)
                titleTextView.text = "Bem vindo ao próx_imo"
                descriptionTextView.text = "O Lorem Ipsum é um texto modelo da indústria tipográfica e de impressão. O Lorem Ipsum tem vindo a ser o texto padrão usado por estas indústrias desde o ano de 1500, quando uma misturou os caracteres de um texto para criar um espécime de livro. Este texto não só sobreviveu 5 séculos, mas também o salto para a tipografia electrónica, mantendo-se essencialmente inalterada. Foi popularizada nos anos 60 com a disponibilização das folhas de Letraset, que continham passagens com Lorem Ipsum, e mais recentemente com os programas de publicação como o Aldus PageMaker que incluem versões do Lorem Ipsum."
            }

            if (pos == 1){
                img.setImageResource(R.drawable.viewpager_img_2)
                titleTextView.text = "Listagem de serviços"
                descriptionTextView.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
            }

            if (pos == 2){
                img.setImageResource(R.drawable.viewpager_img_3)
                titleTextView.text = "Localização"
                descriptionTextView.text = "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
            }

            Log.d("debug", "Args ${getInt(ARG_OBJECT)}")
        }
    }

    // newInstance constructor for creating fragment with arguments
    fun newInstance(page: Int): WelcomeFragment? {
        val fragmentFirst = WelcomeFragment()
        val args = Bundle()
        args.putInt(ARG_OBJECT, page)
        fragmentFirst.arguments = args
        return fragmentFirst
    }
}
