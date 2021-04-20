package com.t0p47.pagingtutorial.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.t0p47.pagingtutorial.R
import com.t0p47.pagingtutorial.databinding.RepoViewItemBinding
import com.t0p47.pagingtutorial.model.Repo

class RepoViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var repo: Repo? = null

    var binding: RepoViewItemBinding? = RepoViewItemBinding.bind(view)

    init {
        view.setOnClickListener {
            repo?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: Repo?){
        if(repo == null){
            val resources = itemView.resources
            binding?.repoName?.text = resources.getString(R.string.loading)
            binding?.repoDescription?.visibility = View.GONE
            binding?.repoLanguage?.visibility = View.GONE
            binding?.repoStars?.text = resources.getString(R.string.unknown)
            binding?.repoForks?.text = resources.getString(R.string.unknown)
        }else{
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: Repo){
        this.repo = repo
        binding?.repoName?.text = repo.fullName

        var descriptionVisibility = View.GONE
        if(repo.description != null){
            binding?.repoDescription?.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        binding?.repoDescription?.visibility = descriptionVisibility

        binding?.repoStars?.text = repo.stars.toString()
        binding?.repoForks?.text = repo.forks.toString()

        var languageVisibility = View.GONE
        if(!repo.language.isNullOrEmpty()){
            val resources = this.itemView.context.resources
            binding?.repoLanguage?.text = resources.getString(R.string.language, repo.language)
            languageVisibility = View.VISIBLE
        }
        binding?.repoLanguage?.visibility = languageVisibility
    }

    companion object{
        fun create(parent: ViewGroup): RepoViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val binding = RepoViewItemBinding.inflate(inflater, parent, false)
            return RepoViewHolder(binding.root)
        }
    }

}