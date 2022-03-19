package hu.bme.aut.android.nagyhazi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.nagyhazi.adapter.TopScoreAdapter
import hu.bme.aut.android.nagyhazi.data.TopScore
import hu.bme.aut.android.nagyhazi.data.TopScoreListDatabase
import hu.bme.aut.android.nagyhazi.databinding.ActivityTopScoreBinding
import hu.bme.aut.android.nagyhazi.fragment.EditTopScoreDialogFragment
import kotlinx.coroutines.*


class TopScoreActivity :
    AppCompatActivity(),
    TopScoreAdapter.TopScoreClickListener,
    EditTopScoreDialogFragment.EditTopScoreDialogListener,
    CoroutineScope by MainScope() {

    private lateinit var binding: ActivityTopScoreBinding
    private lateinit var database: TopScoreListDatabase
    private lateinit var adapter: TopScoreAdapter

    private fun initRecyclerView() {
        adapter = TopScoreAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() = launch {
        val items = withContext(Dispatchers.IO) {
            database.TopScoreDao().getAll()
        }
        adapter.update(items)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = TopScoreListDatabase.getDatabase(applicationContext)
        initRecyclerView()
    }

    override fun onItemChanged(item: TopScore) {
        updateItemInBackgound(item)
    }

    private fun updateItemInBackgound(item: TopScore) = launch {
        EditTopScoreDialogFragment(item).show(supportFragmentManager, EditTopScoreDialogFragment.TAG)
        withContext(Dispatchers.IO) {
            database.TopScoreDao().update(item)
        }
    }

    override fun onItemRemoved(item: TopScore) {
        removeItemInBackground(item)
    }

    private fun removeItemInBackground(item: TopScore) = launch {
        withContext(Dispatchers.IO) {
            database.TopScoreDao().deleteItem(item)
        }
        adapter.removeItem(item)
    }

    override fun onTopScoreEdited(topScore: TopScore) {
        EditItemInBackgound(topScore)
    }

    private fun EditItemInBackgound(item: TopScore) = launch {
        val items =  withContext(Dispatchers.IO) {
            for(j  in database.TopScoreDao().getAll().indices){
                if(database.TopScoreDao().getAll()[j].id == item.id){
                    database.TopScoreDao().deleteItem(database.TopScoreDao().getAll()[j])
                    database.TopScoreDao().insert(item)
                }
            }
            database.TopScoreDao().getAll()
        }
        adapter.update(items)
    }
}