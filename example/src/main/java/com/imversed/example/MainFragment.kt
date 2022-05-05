package com.imversed.example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.imversed.client.Imversed
import com.imversed.example.data.NextPageToken
import com.imversed.example.databinding.FragmentMainBinding
import kotlin.concurrent.thread

class MainFragment : Fragment() {

    private var _imversed: Imversed? = null
    private var _binding: FragmentMainBinding? = null
    private var _bankClient: BankClient? = null
    private var _currencyClient: CurrencyClient? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val imversed get() = _imversed!!

    private val bankClient: BankClient
        get() {
            return _bankClient.or {
                BankClient(imversed.bankBlocking).also {
                    _bankClient = it
                }
            }
        }

    private val currencyClient: CurrencyClient
        get() {
            return _currencyClient.or {
                CurrencyClient(imversed.currencyBlocking).also {
                    _currencyClient = it
                }
            }
        }


    private val actions = listOf(
        Action(R.string.action_total_supply, ::totalSupply),
        Action(R.string.action_total_supply_limit_offset, ::totalSupplyPagination),
        Action(R.string.action_supply_by_denom, ::supplyByDenom),
        Action(R.string.action_denom_metadata, ::denomMetadata),
        Action(R.string.action_denoms_metadata, ::denomsMetadata),
        Action(R.string.action_params, ::params),
        Action(R.string.action_currency_all, ::currencyAll)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _imversed = Imversed()
        imversed.configure(
            host = "qs.imversed.com",
            port = 9090,
            useSecurityTransport = false
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context.applicationContext)
            adapter = ActionsAdapter(::onItemClicked).apply {
                setItems(actions.map { it.title })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imversed.close()
        _imversed = null
        _bankClient = null
        _binding = null
    }

    private fun showMessage(message: Any) {
        binding.recyclerView.post {
            Toast
                .makeText(requireContext(), message.toString(), Toast.LENGTH_SHORT)
                .show()
        }
        Log.d(TAG, "$message")
    }

    private fun onItemClicked(position: Int) {
        actions[position].action()
    }

    private fun action(block: () -> Any) {
        thread {
            safe(TAG, block)?.let(::showMessage)
        }
    }

    private fun <R> action(block: () -> R, nextBlock: (R) -> Any) {
        thread {
            safe(TAG) {
                block()
                    ?.also(::showMessage)
                    ?.let { value ->
                        action {
                            nextBlock(value)
                        }
                    }
            }
        }
    }

    private fun totalSupply() {
        action {
            bankClient.totalSupply()
        }
    }

    private fun totalSupplyPagination() {
        action({
            bankClient.totalSupply(NextPageToken.from(1, 1))
        }) { result ->
            bankClient.totalSupply(result.nextPageToken.copy(limit = 1))
        }
    }

    private fun supplyByDenom() {
        action {
            bankClient.supplyOf("fulldive")
        }
    }

    private fun denomMetadata() {
        action {
            bankClient.denomMetadata("fulldive")
        }
    }

    private fun denomsMetadata() {
        action {
            bankClient.denomsMetadata()
        }
    }

    private fun params() {
        action {
            bankClient.params()
        }
    }

    private fun currencyAll() {
        action {
            currencyClient.currencyAll()
        }
    }

    companion object {
        private const val TAG = "MainFragment"
    }
}