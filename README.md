# Android Exercise

## Github Explorer

#### Your mission: Build a small Android app that lets people browse public Github repos and dive into the details of any repo that catches their eye.

### Must-have features
1. Repositories screen
	- Two-column grid of public repositories. Each column element (repository) should display name, owner avatar and short description.
	- Top app-bar title: “Github Repositories”.
	- Tapping an element navigates to the corresponding details screen.

2. Repository details screen
	- Shows the selected repo’s info: name, description, statistics like stars and forks, last update, and anything else you find relevant.
	- App-bar title = repo name (ellipsised if too long).

3. Search by language **OR** sort options (pick one)
	<ol type="a">
	<li>A search box at the top of the Repositories screen lets the user filter repos by programming language (e.g. “kotlin”, “Rust”).</li>
	<li>Toolbar button opens a menu/dialog letting users order the list by stars, last updated, forks—or combine them if you’d like.</li>
	</ol>

### Nice-to-have extras (pick any, all, or none)
1. Pull-to-refresh on the list.
2. Offline mode so the list still appears without network.
3. Shared element transition from list to details screen.

### Ground rules and recommendations
1. You can use the public Github API (either [REST](https://docs.github.com/en/rest?apiVersion=2022-11-28) or [GraphQL](https://docs.github.com/en/graphql)) with no authentication. Just be mindful that unauthenticated requests have rate limits ([REST](https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28&versionId=free-pro-team%40latest&category=repos&subcategory=repos) and [GraphQL](https://docs.github.com/en/graphql/overview/rate-limits-and-node-limits-for-the-graphql-api?versionId=free-pro-team%40latest&productId=graphql)).
2. Use whatever libraries, patterns, and data-storage solutions you’re comfortable with. 
3. Kotlin + Jetpack Compose are encouraged but not mandatory. KMP and CMP are also accepted.
4. Keep external dependencies reasonable.
5. Time box yourself. Ship what you can; polish over breadth is preferred.
6. It's OK to use AI – just be ready to walk us through any generated code or decisions. We also expect the app to be AI slop free! 

### Why do we ask for this exercise?
- Putting it simply, to understand your development and architecture skills. Please avoid "hammering" it as it might make us consider you are not fit to proceed.


### For the candidate (you) to fill:

- Have you implemented all the features?
- [x] Yes (but without nice-to-haves)
- [ ] No


- Have you used AI? 
- [x] Yes
- [ ] No

**p.s: I decided to make api/impl modules based architecture instead of shared domain module, because I wanted to try this one**
