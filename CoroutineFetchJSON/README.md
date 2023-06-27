In this example, ‘lifecycleScope’ is used for coroutines, which enables proper cancellation and integration with the lifecycle of the component (Activity, Fragment, Service etc). There are other 
approaches available for coroutines as well, such as using ‘GlobalScope’, which is not tied to a specific lifecycle; or creating a custom ‘CoroutineScope’ implementation for better control, not tied 
to a specific lifecycle; or utilizing ‘ViewModelScope’, which is tied to the lifecycle of a ViewModel and offers similar advantages as ‘lifecycleScope’.
